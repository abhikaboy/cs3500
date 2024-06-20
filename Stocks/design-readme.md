# CS 3500 Stock Trading Platform

Part one design explained

## Changes

- Changed the transactions to utilize a set of transaction command-like objects. Abstracts the
  transaction logic
- Created a helper class to manage the conversion between dates and strings

## Design

Our program uses the Model View Controller System to operate.

## Model

The model of our program stores the user portfolios in a hashmap
The Portfolio class presents an individual portfolio and is a container for all the
stock data it may hold.
Stock data is represented in a hashmap where each key is a date, and value is the data for that
given day
Each query of a new stock will call the Alpha Vantage API and store the result in a csv locally.
Upon the creation of a new model, we will load all the local csv's into the program within the Stock
Cache

## View

The view handles all menus and UI in the application.
Uses output stream and prints to user

## Controller

The controller acts as a mediary between the Model and Controller and calls functions from
both classes in order to drive all user functionality.

Keeps track of current "active" portfolio and selects portfolios and stock accordingly

Handles user input and validation.
