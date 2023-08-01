
def sourceCodeCheckout(sourceCodeUrl) {
    if ( sourceCodeUrl =~ /.git/ ) {
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'LocalBranch', localBranch: 'master'],[$class: 'WipeWorkspace'], [$class: 'UserExclusion', excludedUsers: 'amguist']], gitTool: 'Git 1.9.4', submoduleConfig: [], userRemoteConfigs: [[url:sourceCodeUrl]]])
    } else {
        println("Current value of source code url: " + sourceCodeUrl)
    }
}

def groupId() {
    def matcher = readFile('pom.xml') =~ '<groupId>(.+)</groupId>'
    matcher ? matcher[0][1] : null

    String foundGroupId = matcher[0][1]
    return foundGroupId
}

def serviceVersion() {
    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
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