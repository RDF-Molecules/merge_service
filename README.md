[<img src="https://img.shields.io/travis/playframework/play-scala-starter-example.svg"/>](https://travis-ci.org/playframework/play-scala-starter-example)

# MINTE Fusion Policies Service

This web service implements MINTE fusion policies
https://www.researchgate.net/publication/318717156_MINTE_semantically_integrating_RDF_graphs

## Running

Run this using [sbt](http://www.scala-sbt.org/).  If you downloaded this project from http://www.playframework.com/download then you'll find a prepackaged version of sbt in the project directory:

```
sbt run
```

And then go to http://localhost:9000 to see the running web application.

There are several demonstration files available in this template.

## Controllers

- FusionController.merge(policy: String)

  A POST request to merge 1 or N entities using an specific fusion policy e.g., http://localhost:9000/fusion/union
  An array of tuples containing the URIs to be merged should be send in the POST body
  
  ```javascript
[ { "uri1": "uri_1" , "uri2" : "uri_2" } ]
``` 
