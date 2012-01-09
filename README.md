# App47 Blackberry Agent

The App47 Blackberry Agent enables realtime analytics for Blackberry Apps. 

## Configuration

In order for your Blackberry app to communicate with App47, you'll need to update the `AgentResources.rrc` file with your App's corresponding ID. You can find this ID on the App47 dashboard for your App. Once you've done that, follow the build instructions and you'll be good to go!

## Building and including in your Blackberry App

To build, you'll need to have Blackberry's JDE installed, Ant, and JAVA_HOME defined. Clone this repository or download the source archive. You'll need to provide a `local.properties` file -- see the default one (aptly named `default.properties`) to see the format. Simply specify the path to a valid JDE installation. 

Then type `ant`. This will produce a series of Blackberry specific files in `target/dist` -- .cod, .jad, .cso -- these are the files you'll ultimately deploy to a device. What's more, there will be a .jar file in the `target/dist` directory that you can import into your IDE (i.e. Eclipse) so as to facilitate compilation.  

## Sample App

See our [Sample Blackberry App](https://github.com/App47/blackberry-agent-example) for more details on how to use the App47 Blackberry Agent.

# License

The MIT License

Copyright (c) 2012 App47, Inc.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE