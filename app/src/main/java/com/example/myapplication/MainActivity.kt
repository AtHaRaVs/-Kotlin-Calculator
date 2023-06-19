package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null                     // variable to see whats the input from the user (declaration)
    var lastNumeric: Boolean = false                          // to check whether last entry by user is number
    var lastDot:Boolean = false                               // to check if user had entered dot as last entry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)                 // initializing the variable
    }

    fun onDigit(view: View){                                 // fun which will run when digits are clicked, view here means UI element
        tvInput?.append((view as Button).text)               // here we append the number on digit to screen, cant use text on view, so converted to btn
        lastNumeric = true                                   // since it will be a digit so setting the flag to true
        lastDot = false                                      // setting decimal flag to false
    }


    fun onClr(view: View){                                   // fun which will clear everything on screen when clr is clicked
        tvInput?.text = ""                                   // changing text to empty string
    }


    fun onDecimalPoint(view: View){                          // fun which will run when decimal is encountered or clicked
        if(lastNumeric && !lastDot){                         // last entry should be number, and not decimal (using if else to check it)
            tvInput?.append(".")                             // if condition is met appending (.) to the screen
            lastNumeric = false                              // since dot is appended this flag will turn into false
            lastDot = true                                   // setting this flag to true
        }
    }


    fun onOperator(view: View){                              // fun which will run when operators are clicked
        tvInput?.text?.let {                     // first it will check if some text is there on the screen, cause we will not add operator if empty
            if(lastNumeric && !isOperatorAdded(it.toString())){  // last number should be checked also a fun to check if two operators aren't side by side
                tvInput?.append((view as Button).text)           // if conditions are met we will append the operator on the screen
                lastNumeric = false                              // setting flag to false
                lastDot = false                                  // setting flag to false
            }
        }
    }


    fun onEqual(view: View){                                    // fun which will run when we equate the equation (press = sign on cal)
        if(lastNumeric){                                        // first it will check if some number is there or not
            var Value = tvInput?.text.toString()                // we will make the numbers + operators on the screen to string eg(32+42) to "32+42"
            var prefix = ""                                     // declare plus initialize a variable which will look for first entry on screen (for -ve numbers)
            try{                                                // try catch block so the app don't crash
                if(Value.startsWith("-")){                 // it will check if first thing on screen is - eg(-32 +42), here first entry is (-)
                    prefix = "-"                                 // we will set the prefix flag to (-)
                    Value = Value.substring(1)           // now we take only the part after the (-) sign eg(-32+43) become (32+43)
                }
                if(Value.contains("-")) {                   // now we will make subtraction functionality
                    val splitValue = Value.split("-")   // now we will split the string, splitting is done in array eg(32-33) would be 32,33 (in array)
                    var one = splitValue[0]                      // we will put the left side of operator in one eg(32-33), it will 32 in one
                    var two = splitValue[1]                      // we will put the right side of operator in two eg(32-33), it will 33 in one
                    if(prefix.isNotEmpty()){                     // here we will check if the prefix was empty or not
                        one = prefix + one                       // if prefix was not empty we will append prefix to string (one) so it becomes (-32+33) again
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString()) // here we will calculate the subtraction
                }else if (Value.contains("+")) {             // everything same just changed to (+)
                    val splitValue = Value.split("+")     // here split will take place on both side of (+) operator
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString()) // here we will calculate the addition
                }else if (Value.contains("/")) {                // everything same just changed to (/)
                    val splitValue = Value.split("/")       // here split will take place on both side of (/) operator
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString()) // here we will calculate the division
                }else if (Value.contains("*")) {                  // everything same just changed to (*)
                    val splitValue = Value.split("*")          // here split will take place on both side of (*) operator
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString()) // here we will calculate the multiplication
                }
            }catch (e:ArithmeticException){                              // it will catch all arithmetic exceptions and give response

            }
        }
    }

    private fun removeZeroAfterDot(result: String):String{              // fun to remove .0 after calculation is performed eg(32+33 = 65.0) to 65 only (since double)
        var value= result                                               //it will take a string which will be result as parameter and store it in variable
        if(result.contains(".0")){                                 // if condition is met i.e string contain .0 eg("33.0")
            value = result.substring(0, result.length - 2)              // it will change value of variable from length 0 to total length - 2 eliminating (.) and (0)
        }
        return value                                                    // it will return the variable, we used this on every point where we calculated
    }

    private fun isOperatorAdded(value: String): Boolean{                 // function will keep a check no two operators come side to side
        return if(value.startsWith("-")){                           // a condition is checked first, so that -ve numbers (-) doesn't count as operator
            false                                                        // if theres a negative number it will return boolean false
        }else{
            value.contains("/") || value.contains("+") || value.contains("-") || value.contains("*") //otherwise check for other operators
        }                                                                                                                 // contains return true if condition
    }                                                                                                                     // is met
}
