Customer orders products

Narrative:
In order to buy interesting products
As a customer
I want to browse the available products, pick the ones that I like and order them

Scenario: client puts some products into baskets and checks out
Given Products are available
When I add any product to basket
Then Basket should contain 1 item
When I checkout
Then I should be able to submit order with 1 product
When I submit the order
Then That order should be confirmed
