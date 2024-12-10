## List of Added Features To CodeLANG
**Run the `CODE`** to test it out.
- **Added the CAST operators, such as `CAST_BOOL`, `CAST_INT`, `CAST_STRING`, and `CAST_FLOAT`.**
- **Added functions which can be implemented via `BEGIN FUNCTION NAME: <PARAMS> ... END FUNCTION`**
- **Added the `RETURN` statement to return a value from a function.**`
- **Added the `CODE Native Interface` to allow the user to call native functions from the code. Unlike most programming languages, `NATIVE` functions are not implemented via low-level languages such as C, or RUST, but rather work with the Java Virtual Machine (JVM).**
- **Added the `THREADED` function to allow the user to run a function in a separate thread.**
- **Added the `MYSQLCONNECT` library of functions to allow the user to connect to a MySQL database.**
- **Added the `@import` keyword to allow the user to use `native` operations. These are implemented via the Java Virtual Machine system, and utilizes both assembly and Java code.**
```CODELang
   @import MySQLConnector
    
    BEGIN CODE
        THREADED: sampleThreaded
        OBJECT instance = MySQLConnector: "mysql://localhost:3306", "root", "password", "database"
    END CODE
    
    BEGIN FUNCTION sampleThreaded:
        DISPLAY: "This is from another thread!"
    END FUNCTION
```
- **Added standard collection classes such as `LIST`, `SET`, and `MAP`.**
```CODELang
    @import List
    @import Map
    @import Set
    

    BEGIN CODE
        OBJECT myArray = new ARRAY: 5;
        OBJECT myMap = new MAP:
        myArray.add: 3
        DISPLAY: myArray.get: 0
    END CODE
```
- **Added `ThreadUtils` for thread control, `Timer` for timer operations and benchmarking.**
- **Added `HTTP` for network operations. Currently, only `HTTP.get` is supported to fetch data using the internet.**
- **Added `WebView` to allow GUI operations inside `CODE` using HTML.**
- **Added string interpolation.**

```
    # Code to display a simple web page
    @import WebView
    @import HTTP


    BEGIN CODE
        OBJECT html = GET_HTML:
        OBJECT ui = new WebView:
        ui.view: html
    END CODE


    BEGIN FUNCTION GET_HTML
        STRING x
        DISPLAY: "Enter the website to render: "
        SCAN: x
        DISPLAY: "[$]Great! Now rendering the website [x]!"

        OBJECT web = new HTTP:
        OBJECT htmlCode =  web.get: x
        RETURN htmlCode
    END FUNCTION

```
