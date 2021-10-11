pipeline {
    agent any
    stages {
        stage("Read from Maven POM"){
            steps{
                script{
                    projectArtifactId = readMavenPom().getArtifactId()
                    projectVersion = readMavenPom().getModelVersion()
                }
                echo "Building ${projectArtifactId}:${projectVersion}"
            }
        }
//         stage("Test"){
//             steps {
//                 bat "mvn -version"
//                 bat "mvn test"
//             }
//         }
        stage("Build JAR file"){
            steps{
                bat "mvn install -Dmaven.test.skip=true"
            }
        }
        stage("Build image"){
            steps {
                echo "Building service image and pushing it to DockerHub"
                    withCredentials([usernamePassword(credentialsId: 'Docker', usernameVariable: "dockerLogin",
                        passwordVariable: "dockerPassword")]) {
                            bat "docker login -u ${dockerLogin} -p ${dockerPassword}"
                            bat "docker image build -t ${dockerLogin}/${projectVersion} ."
                            bat "docker push ${dockerLogin}/${projectVersion}"
                        }
                echo "Building image and pushing it to DockerHub is successful done"
            }
        }
        stage("Deploy"){
            steps{
                bat "docker-compose --file docker-compose.yml up --detach"
                timeout(time: 60, unit: 'SECONDS') {
                waitUntil(initialRecurrencePeriod: 2000) {
                    script {
                        def result =
                        sh script: "curl --silent --output /dev/null http://localhost:8282/api/v1/auth/signin",
                        returnStatus: true
                        return (result == 0)
                        }
                    }
                }
                echo "Server is up"
            }
        }
//         stage("Newman Test"){
//             steps{
//                 echo "Starting Newman Test"
//                 bat "newman run --disable-unicode https://www.getpostman.com/collections/345d1665e5bdd9ca448e"
//             }
//         }
//         stage("JMeter Loading Test"){
//             steps{
//             echo "Starting the JMeter Loading Test"
//             bat "jmeter -jjmeter.save.saveservice.output_format.xml -n -t D:/RestaurantAPI.jmx -l D:/report.jtl"
//             }
//         }
    }
    post {
        always {
            cleanWs()
        }
    }
}
