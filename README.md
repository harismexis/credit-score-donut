## Overview:

The project is written in Kotlin and uses MVVM Architecture (Model - View - ViewModel).
Frameworks have been used i.e. Android JetPack Components (LiveData, ViewModel, Coroutines),
RxJava, Retrofit, Dagger.

## Description:

The Project contains one screen (MainActivity) where the user can see their credit score in a donut view.
A progress bar has been used for showing user's score in the donut view. MainActivity is injected with a
MainViewModel and observes the LiveData (credit score info) that the ViewModel exposes. MainViewModel has
the business logic for the Activity. It observes network connection and when internet is active it will execute
a network call (using Coroutines) to fetch the credit score data from the given end point. Retrofit has been
used for creating the related API. If the response is retrieved successfully, it will be converted to a CreditUiModel
and assigned to the LiveData, so the Activity will be notified and retrieve the UiModel to update the donut view.

## Unit Tests:

The project contains UnitTests for testing the business logic of the application.
Mockito has been used for mocking the dependencies of the classes under test and also
some necessary utilities for setting up the tests with coroutines and live data.