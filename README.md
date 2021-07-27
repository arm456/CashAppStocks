Building App:-
Unzip the file & on running the app, we have 3 buttons which make an API call from either of the following endpoints based on button clicked:-
1. Valid Stock Options - https://storage.googleapis.com/cash-homework/cash-stocks-api/portfolio.json
2. Invalid Stock Options - https://storage.googleapis.com/cash-homework/cash-stocks-api/portfolio_malformed.json
3. Empty Stock Options - https://storage.googleapis.com/cash-homework/cash-stocks-api/portfolio_empty.json


Description:-
CashAppStocks is a simple stocks info Android application built using MVVM architecture to separate UI from application logic.
The app has 3 options to handle the 3 kinds of responses we get from the backend. On clicking the 1st option,
we display a list of stocks with price & number of stocks available info. 2nd option makes a call to receive the malformed JSON & we show the error response.
On clicking 3rd option, we receive empty response & show a hardcoded string(Empty Portfolio). For 2nd & 3rd options, a simple text view is used to show the relevant message.

The Header url of the API used for getting stock info : https://storage.googleapis.com/cash-homework/cash-stocks-api


Assumptions:-
• Shows a progress indicator every time we try to get the response back from the backend.
• Display a list of stocks if the response is valid & has data
• Display relevant text on receiving error response (or) empty response (or) due to network failure.
• We define two states - Success & Failure to identify the response we get back from the backend.


Language and Libraries used:-
• Kotlin - https://kotlinlang.org
• Android Architecture Components : ViewModel and LiveData - https://developer.android.com/topic/libraries/architecture/index.html
• Kotlin Coroutines for making asynchronous API calls - https://developer.android.com/kotlin/coroutines 
• Retrofit : Network Calls - https://square.github.io/retrofit/
• Dagger 2 : Dependency Injection - https://github.com/google/dagger
• Mockito: Unit Testing - https://site.mockito.org/
https://proandroiddev.com/how-to-unit-test-code-with-coroutines-50c1640f6bef


GitHub Link:- https://github.com/arm456/CashAppStocks


Time Spent:- Around 5 hours