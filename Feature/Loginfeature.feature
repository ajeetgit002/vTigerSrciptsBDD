Feature: Login Page and Create New Leads and delete

Background: Steps are common for all scenario
 Given User Open Browser
     When User Open URL "http://localhost:8888/"
     Then User On login Page title Should be "vtiger CRM 5 - Commercial Open Source CRM"
     When User Enter UserName as "admin" and Password as "admin" 
     And click on Login Button
     Then User On Home page Title Should be "admin - My Home Page - Home - vtiger CRM 5 - Commercial Open Source CRM"
      When User Click on Sales link 
        And User Clcik on Lead
        Then User On Leads Datails Page and View Confirmation Header should be  "Sales > Leads"

Scenario: Successfully Login User Enter valid credential
   
    When User Click on cerate new Lead icon(plus Button)
    Then user On create new Leads and View Comfirmation Header should be "Creating New Lead"
    When User Pass full info for create new Leads 
    And Click On Save Button
    Then Page Title should be "admin - Sales - Leads - vtiger CRM 5 - Commercial Open Source CRM"
    
    
Scenario: Search new leads which is create By user 
       
        When User Enter First Name In Search Text Box
        And User Select first Name From DropDown 
        And User Clcik On Search Button
        Then User Found Leads Name In Table 

Scenario: User Succefully Delete New Leads
         When User Enter First Name In Search Text Box
        And User Select first Name From DropDown 
        And User Clcik On Search Button
        Then User Found Leads Name In Table 
        When user Clcik On Ckeck Box 
        And Confirm alert Pop occur and User Accept 
        Then User Confirm text view "No Lead Found !"
										
     