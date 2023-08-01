
def sourceCodeCheckout(sourceCodeUrl) {
    if ( sourceCodeUrl =~ /.git/ ) {
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'LocalBranch', localBranch: 'master'],[$class: 'WipeWorkspace'], [$class: 'UserExclusion', excludedUsers: 'amguist']], gitTool: 'Git 1.9.4', submoduleConfig: [], userRemoteConfigs: [[url:sourceCodeUrl]]])
    } else {
        println("Current value of source code url: " + sourceCodeUrl)
    }
}