# Fluent Tree Iterator [In Progress]
## Overview
[![Build Status](https://travis-ci.org/ifillbrito/trees.svg?branch=master)](https://travis-ci.org/ifillbrito/trees)
[![codecov](https://codecov.io/gh/ifillbrito/trees/branch/master/graph/badge.svg)](https://codecov.io/gh/ifillbrito/trees)

This library provides an abstract class that can be extended to enabled declarative manipulation of trees in existing objects.

## Example:
The following example shows some of the methods of the API (still in development). The conditions (``forAll``, ``forPath``) and functions (lambdas) used to manipulate the nodes were written arbitrarily, so the semantic described in this example might not make any sense. The goal at the moment is to see how the API looks like.

```java
Node inputRoot = createNode();
List<Node> collection = new ArrayList<>();
Map<String, Node> leafsMap = new HashMap<>();
Map<String, Node.Color> colorMap = new HashMap<>();
Map<Node.Color, Set<Node>> nodesByColorMap = new HashMap<>();
Map<Node.Color, Set<String>> nodeNamesByColorMap = new HashMap<>();

MyTreeIterator.of(inputRoot)
    // configure the iteration:
    // execution order; filtered, ignored and/or skipped nodes
    .iterate()
        .topDownExecution()
        .forAll(Node::isLeaf)
        .take(4)
        .filter()
        .end()

    // modify (or just do something with the nodes), replace them, or remove them
    .edit()
        .forAll(node -> node.isRed() && node.isValueEven())
        .apply(node -> node.setValue(n -> n * 2))
        .forAll(node -> node.isYellow() && node.getValue() > 15)
        .replace(node -> Node.create("z", 20, Node.Color.RED))
        .forPath("/a/b/.*")
        .remove()
        .end()

    // list of nodes
    .collect(collection)
        .forPath("/a/b/[a-z]^")
        .skip()
        .end()

    // node map by name
    .collect(leafsMap, Node::getName)
        .forAll(Node::isLeaf)
        .take(3)
        .bottomUpExecution()
        .filter()
        .end()

    // node color map by name
    .collect(colorMap, Node::getName, Node::getColor)
        .forAll(Node::isLeaf)
        .filter()
        .end()

    // group nodes by color
    .group(nodesByColorMap, Node::getColor, HashSet::new)
        .forAll()
        .take(2,4)
        .filter()
        .end()

    // group nodes by color
    .group(nodeNamesByColorMap, Node::getColor, Node::getName, HashSet::new)
        // node-path condition
        .forAll((node, path) -> true)
        .takeOccurrence(4)
        .filter()
        // node-parent-path condition
        .forAll((node, parent, path) -> true)
        .ignore()
        .end()

    // Wrappers: Useful if a reference to the parent, grandparent, etc is needed.
    .edit()
        // work with a node wrapper in the next statement
        // the wrapper contains: object, parent, path
        .resolveParents()
        .forAll(nodeWrapper -> nodeWrapper.getParent() == null)
        .resolveParents()
        .apply(node -> node.getParent().setNode(node))
        .end()

    // work with a node wrapper for all operations in edit()
    // the wrapper contains: object, parent, path
    .resolveParents()
    .edit()
        .forAll(nodeWrapper -> nodeWrapper.getParent() == null || nodeWrapper.getPath() == null)
        .apply(nodeWrapper -> nodeWrapper.getParent().getParent().getParent().getNode())
        .end()

    // define the class to be used for the next operations
    // it can be used to disable wrapper, or to avoid
    // "xyz instance of SomeObject" conditions
    .use(Node.class)
    .edit()
        .forAll(Node::isRed)
        .remove()
        .end()
    .execute();
``` 

## License

Copyright 2018 by Grebiel Ifill

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

---
Designed by Eduard Beutel and Grebiel Ifill. The design pattern can be found at https://github.com/eduardbeutel/design-patterns.
