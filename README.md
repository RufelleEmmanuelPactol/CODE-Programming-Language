## List of Added Features To CodeLANG
**Run the `CODE`** to test it out.
- **Added the CAST operators, such as `CAST_BOOL`, `CAST_INT`, `CAST_STRING`, and `CAST_FLOAT`.**
- **Added functions which can be implemented via `BEGIN FUNCTION NAME: <PARAMS> ... END FUNCTION`**
- **Added the `RETURN` statement to return a value from a function.**`
- **Added the `CODE Native Interface` to allow the user to call native functions from the code. Unlike most programming languages, `NATIVE` functions are not implemented via low-level languages such as C, or RUST, but rather work with the Java Virtual Machine (JVM).**
- **Added the `THREADED` function to allow the user to run a function in a separate thread.**
- **Added the `MYSQLCONNECT` library of functions to allow the user to connect to a MySQL database.**
- **Added the `INCLUDE` keyword to allow the user to use the Standard Template Library (STL) functions. Invokable via `INCLUDE StandardLibraryFunctions`.**
```CODELang
    INCLUDE "std::THREADED"
    INCLUDE "std::MYSQLCONNECT"
    
    BEGIN CODE
        THREADED: sampleThreaded
        OBJECT instance = MYSQLCONNECT: "mysql://localhost:3306", "root", "password", "database"
    END CODE
    
    BEGIN FUNCTION sampleThreaded:
        DISPLAY: "This is from another thread!"
    END FUNCTION
```
- **Added standard collection classes such as `ARRAY`, `LIST`, `SET`, and `MAP`.**
```CODELang
    INCLUDE "std::ARRAY"
    INCLUDE "std::MAP"
    INCLUDE "std::SET"
    

    BEGIN CODE
        OBJECT myArray = NEW ARRAY: 5;
        OBJECT myMap = NEW MAP:
        myArray.add: 3
        DISPLAY: myArray.get: 0
    END CODE

```