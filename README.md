# forms-library
Extension of robot framework [remoteswinglibrary](https://github.com/robotframework/remoteswinglibrary) to include support for oracle forms.

Oracle forms applications typically run as an applet in the browser and are built using oracle specific components rather than standard swing components.
The goal of this library is to extend the robotframework remoteswinglibrary with the necessary keywords to drive an oracle forms application.

This library assumes that the oracle forms applications is started directly using java web start and not as a browser plugin.


## TODO
* enable dynamic debug logging
* Attach to running process instead of launch new one
* test screenshot
* add sample jnlp file
* add sample test cases

## Support

This library has been created to support a specific oracle forms application.
It is free for anyone to use and modify. YMMV and there is no free support for it.