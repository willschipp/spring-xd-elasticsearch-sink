## Simple Elasticsearch Sink by SpringXD

### Overview

Simple Sink that expects either a Map or JSON String and will persist it into an Elasticsearch Cluster.


### Options

Must specify

- index (```--index=```)
- type (```---type=```)
- hosts (```---hosts='localhost,otherhost'```)


### Todo

- add a field filter for data that shouldn't be indexed 