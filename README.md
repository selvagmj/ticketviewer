# Zendesk Ticket Viewer
This application provides you with a simple command line interface to query zendesk tickets in bulk and individually.

### Installation and Usage:
1. Create your account in Zendesk ([Refer this link](https://support.zendesk.com/hc/en-us/articles/4408823799962-How-do-I-create-a-Support-trial-account-)) and make sure to generate API token for your account. ([Refer this link](https://support.zendesk.com/hc/en-us/articles/4408889192858-Generating-a-new-API-token))
2. There are 2 ways to use the application

    a. **Use the Executable Jar:**
    1. Download the [ticketviewer-0.0.1-jar-with-dependencies.jar](https://github.com/selvagmj/ticketviewer/raw/main/target/ticketviewer-0.0.1-jar-with-dependencies.jar) (Use chrome to download jar. Firefox cannot download jar)
    2. java -jar ticketviewer-0.0.1-jar-with-dependencies.jar domain=https://{account name}.zendesk.com username={user email} apitoken={api token generated in first step}
    
    You can also enable logging by adding the option logging=true. Logging disabled by default.
    
    java -jar ticketviewer-0.0.1-jar-with-dependencies.jar logging=true domain=https://{account name}.zendesk.com username={user email} apitoken={api token generated in first step}
    
    b. **Run from maven source:**
    1. Download this github repository.
    2. Add your domain, username and apitoken in src/main/resources/credentials.properties
    3. Run Service.java file to query ticket APIs.

### Response Structure:
You can perform 2 kinds of operations using this application.

**1. View your tickets**

After starting the application you can press **1** to access your tickets. 
    
The fields displayed are Id, Priority, Subject, Requester Id, Updated At, Status and Assignee Id. When a field is empty it is indicated by empty space.
    
The first 25 tickets will be displayed in the first page. If there are more tickets available to see you can go to next page by typing **next**. "next" option will only be available in command line if more tickets are available. Likewise if you want to go to previous page you can type **"previous"**. "previous" option is available only when previous tickets are available. 
    
**2. View ticket by ticket Id**

After starting the module you can press **2** to access your individual tickets by id.
    
The fields displayed in individual tickets are ticket Id, Requester Id, Subject, Description, Created At, Updated At, Status, Assignee Id, Tags and Priority. When a field is empty it is indicated by empty space.


You can return to main menu anytime by entering **"main"**. Whenever you enter an invalid operation you will be pushed back to main menu. When you enter an invalid operation in main menu the application will quit.

Press 3 to quit application
