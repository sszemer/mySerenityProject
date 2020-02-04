Lookup a currency
Narrative:
In order to go to thailand
As an turist
I want to look up currency values

Scenario: Looking up the currency of thailand

Meta:
@nbp

Given We know the current rate for THB
When The rate is less than 0.13
Then We can go on vacation