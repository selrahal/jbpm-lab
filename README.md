jBPM Lab
========
Lab material for jBPM


## Background ##
Your airline company, RedEye, is trying to stay relevant in order to complete with some of the bigger organizations. One of the biggest issue you are currently facing is the slow time to market for changes to your first class upgrade process. Several C-level executives have decided that you will need to offer a mechanism for passengers to attempt a first class upgrade. However they are still deciding the criteria and steps that will be used to either approve or deny their request. Your mission, if you choose to keep your job, is to implement a webservice that is backed by jBPM for these attempts at first class upgrades. The boss wants to see a simlpe functional example during Phase 1, and then an actual decision taking place in Phase 2. Furthermore, after creating the application that will recieve the requests for upgrade you must _not_ undeploy it. This means the changes from Phase 1 to Phase 2 will need to be done without a redeploy!


## Project Structure ##
* first-class-upgrade : Parent project for dependency management
* first-class-uprgade-app : The WAR that will expose your REST service
* first-class-upgrade-kjar : This artifact will hold the definition of the first class upgrade business process
* first-class-upgrade-model : This artifact will hold the common classes used by all other in the project

## Prerequisites ##
1. Install JBoss EAP 6.4
2. Install BPMS 6.1.0 into EAP 6.4
3. Ensure you can start up, and log into, BPMS 6.1.0
4. Download the BPMS 6.1.0 maven repository and unzip it into your local maven cache (default ~/.m2/repository)
5. Clone this project


## Phase 1 ##
1. Navigate to parent redeye project
2. Execute 'mvn clean install' in the first-class-upgrade project
3. Upload first-class-upgrade-model jar to BPMS
4. Create OU in BPMS (For the 'RedEye' organization)
5. Create repo in BPMS (For the 'FirstClass' team)
6. Create new project ('First Class Upgrades') with GAV com.rhc.jbpm.redeye:first-class-upgrade-kjar:1.0.0
7. Add dependency in first-class-upgrade-kjar on first-class-upgrade-model
8. Set the deployment type to 'PER_REQUEST' in the deployment descriptor.
9. Create 'FirstClassUpgrade' business process with Start->Script(Log)->End
* Should have a process variable named 'requestor' with type com.rhc.jbpm.redeye.model.Person
* Log task should print out the value of this process variable
10. Build and Deploy the first-class-upgrade-kjar.
11. Deploy first-class-upgrade-app to EAP and test a request by visiting the context root

## Phase 2 ##
1. Alter your diagram to include an exclusive gateway that will be used to decide whether or not the upgrade request is approved
2. For the approve case and the deny case include a node that will log the appropriate message
3. Upgrade the version of first-class-upgrade-kjar to 1.0.1
4. Without undeploying the first-class-upgrade-app, build and deploy the first-class-upgrade-kjar 
5. Confirm that you can start a new process, and check the logs for the message that was sent to the customer!
6. Now you can even remove the 1.0.1 deployment and see how the behavior reverts!
