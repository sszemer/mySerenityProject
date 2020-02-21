Lookup a currency
Narrative:
In order to go to thailand
As an turist
I want to look up currency values

Scenario: Looking up the currency of thailand

Meta:
@nbp
@tag id: test-0001

Given We know the current rate for THB
When The rate is less than 0.1300
Then We can go on vacation