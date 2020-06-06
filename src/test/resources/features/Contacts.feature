#Author: siva Arivalagan
#Keywords Summary : Salesforce Application - Contacts Creation

Feature: Contact Creation

Background: 
	Given user launch the salesforce application 
	
	###
	###  As a Sales Operation User, we create Contact.
	###

	
@RubrikDemo @Contacts @CreateContacts @ContactasLightingAdmin @RegressionSuite
Scenario: Contact Creation for 'lightingAdmin'

	Given user able to login 
  Then user able to create a new contact for 'lightingAdmin'
  And validate contact creation is successful
  Then logout the Application