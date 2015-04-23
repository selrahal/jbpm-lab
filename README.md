jBPM Lab
========
Lab material for jBPM


## Background ##
Your airline company, RedEye, is trying to stay relevant in order to complete with some of the bigger organizations. One of the biggest issue you are currently facing is the slow time to market for changes to your first class upgrade process. Several C-level executives have decided that you will need to offer a mechanism for passengers to initiate an attempt at a first class upgrade. However they are still deciding the criteria and steps that will be used to either approve or deny their request. Your mission, if you choose to keep your job, is to implement a webservice that is backed by jBPM for these attempts at first class upgrades. The boss wants to see a simlpe functional example during Phase 1, and then an actual decision taking place in Phase 2. Lastly, during Phase 3 we should allow human intervention (from one of our customer service representatives) that will take a declined request and approve it. Furthermore, after creating the application that will recieve the requests for upgrade you must _not_ undeploy it. This means the changes from Phase 1 to Phase 2 to Phase 3 will need to be done without a redeploy!


## Project Structure ##
* first-class-upgrade : Parent project for dependency management
* first-class-uprgade-app : The WAR that will expose your REST service
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

## Phase 3 ##
1. Add a process variable for 'override', should be boolean
2. Add a human task after the declined log
3. Set the 'groups' property of the human task equal to 'csr' for customer service reps
4. Add 'firstname' and 'lastname' to data inputs
5. Add 'override' to data outputs
6. Create assignments (you can use MVEL to get properties on a process variable like '#{requestor.firstName}')
7. Create task form
8. Add input for first name and last name to task form
9. Add output for override to task form
10. Save the task form
11. Add a gateway after human task that uses the process variable 'override' to either log a hard decline or go to the approval step
12. Upgrade the project version to 1.0.2 and build & deploy
13. Add csr to your roles (via EAP)
14. Practice manually overriding the request, either by approving or denying

## Phase 4 (bonus) ##
1. Rest interface has been deprecated! Change to embedded jbpm now!
2. Add <repository> element under active profile
 ```xml
       <repository>
          <id>guvnor-m2-repo</id>
          <name>BRMS Repository</name>
          <url>http://localhost:8080/business-central/maven2/</url>
          <layout>default</layout>
          <releases>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
          </releases>
          <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
          </snapshots>
        </repository>
 ```
3. Add <server> element to specify authentication information
 ```xml
    <server>
      <id>guvnor-m2-repo</id>
      <username>admin</username>
      <password>admin</password>
      <configuration>
        <wagonProvider>httpclient</wagonProvider>
        <httpConfiguration>
          <all>
            <usePreemptive>true</usePreemptive>
          </all>
        </httpConfiguration>
      </configuration>
    </server>
 ```
4. Inspect Embedded jBPM code
5. Run the app and confirm functionality
