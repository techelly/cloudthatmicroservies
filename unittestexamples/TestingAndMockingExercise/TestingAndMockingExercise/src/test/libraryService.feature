Feature: Get books from the library
			
	Scenario: Get all books from the library
		Given I have a Library Business Service instance
		When I ask it to get all books
		Then it returns a list of 5 books
		And the list contains the book "Cryptonomicon"
		And the list contains the book "The Last Count of Monte Cristo"

	Scenario: Get a book with a given ISBN number
		When I ask it to get the book with ISBN 978-0393351590
		Then it returns one book
		And the book is "Flash Boys"

	Scenario: Get a book with a given title
		When I ask it to get the book title that matches "over" 
		Then it returns one book
		And the book is "The Overstory"
