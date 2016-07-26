# forms-library
A robotframework library for testing Oracle Forms applications.
This library started as an extension of robot framework [remoteswinglibrary](https://github.com/robotframework/remoteswinglibrary) to include support for oracle forms.

Oracle forms applications typically run as an applet in the browser and are built using oracle specific components rather than standard swing components.
The goal of this library is to extend the robotframework remoteswinglibrary with the necessary keywords to drive an oracle forms application.

This library assumes that the oracle forms applications is started directly using java web start (JNLP) and not as a browser plugin.


## TODO
* Attach to running process instead of launch new one
* test screenshot (using wxpython, it takes screenshot of active desktop)
* add sample test case

## Support

This library has been created to support a specific oracle forms application, but should work on other Oracle Forms applications too. It is free for anyone to use and modify. Pull requests are welcome.
Free support is not available.