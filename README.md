# Zendesk Ticket Viewer

1. Create your account in Zendesk and make sure to generate API token for your account. [Refer this link](https://support.zendesk.com/hc/en-us/articles/4408889192858-Generating-a-new-API-token)
2. There are 2 ways to use the application

    a. **Use the Executable Jar:**
    1. Download the [ticketviewer-jar-with-dependencies.jar](https://github.com/selvagmj/ticketviewer/raw/alpha_version/target/ticketviewer-jar-with-dependencies.jar)
    2. java -jar ticketviewer-jar-with-dependencies.jar domain=https://{account name}.zendesk.com username={user email} apitoken={api token generated in first step}
    
    You can also enable logging by adding the option logging=true. Logging disabled by default.
    
    java -jar ticketviewer-jar-with-dependencies.jar logging=true domain=https://{account name}.zendesk.com username={user email} apitoken={api token generated in first step}
