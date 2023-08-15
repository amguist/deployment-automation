
def sourceCodeCheckout(sourceCodeUrl) {
    if ( sourceCodeUrl =~ /.git/ ) {
        checkout([$class: 'GitSCM', branches: [[name: '*/main']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'LocalBranch', localBranch: 'main'],[$class: 'WipeWorkspace'], [$class: 'UserExclusion', excludedUsers: 'amguist']], gitTool: 'Git 1.9.4', submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'githubToken', url:sourceCodeUrl]]])
    } else {
        println("Current value of source code url: " + sourceCodeUrl)
    }
}

def serviceBuild(ver,group,artifactId) {
    def startTime = System.currentTimeMillis()
    def endTime
    def buildTime
    def isBuildSuccessful = false

    try {
        println("Running Maven Build ...")
        sh 'mvn clean install'
        isBuildSuccessful = true
        endTime = System.currentTimeMillis()
        buildTime = endTime - startTime
    } catch (exception) {
        println("Jenkins Build Failed ${env.BUILD_URL}")
        throw exception
    }
}

def groupId() {
    def matcher = readFile('pom.xml') =~ '<groupId>(.+)</groupId>'
    matcher ? matcher[0][1] : null

    String foundGroupId = matcher[0][1]
    return foundGroupId
}

def serviceVersion() {
    def matcher = readFile('pom.xml') =~ '<revision>(.+)</revision>'
    matcher ? matcher[0][1] : null

    String foundPomVersion = matcher[0][1]
    if(foundPomVersion =~ /SNAPSHOT/) {
        def fullVersion = foundPomVersion.split('-')
        version = fullVersion[0]
    } else {
        version = foundPomVersion
    }
    return version;
}

return this