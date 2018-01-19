# Abstract Tree Iterators [Prototype]

After the implementation has been done, this project will provide abstract classes that can be used to manipulate trees in a functional fashion.

## Example:

```java
Node inputRoot = createTree();
List<Node> collection = new ArrayList<>();
Map<String, Node> leafsMap = new HashMap<>();
Map<String, Node.Color> colorMap = new HashMap<>();
Map<Node.Color, Set<Node>> nodesByColorMap = new HashMap<>();
Map<Node.Color, Set<String>> nodeNamesByColorMap = new HashMap<>();

MyTreeIterator.of(inputRoot)
        // modify (or just do something with the nodes), replace them, or remove them
        .edit()
            .forall(node -> node.isRed() && node.isValueEven())
            .apply(node -> node.setValue(n -> n * 2))
            .forall(node -> node.isYellow() && node.getValue() > 15)
            .replace(node -> Node.create("z", 20, Node.Color.RED))
            .forPath("/a/b/.*")
            .remove()
        .end()
        // collect in list
        .collect(collection)
            .forPath("/a/b")
            .skip()
        .end()
        // node map by name
        .collect(leafsMap, Node::getName)
            .forall(Node::isLeaf)
            .add()
        .end()
        // node color map by name
        .collect(colorMap, Node::getName, Node::getColor)
            .forall(Node::isLeaf)
            .add()
        .end()
        // group nodes by color
        .group(nodesByColorMap, Node::getColor, HashSet::new)
            .forall()
            .add()
        .end()
        // group nodes by color
        .group(nodeNamesByColorMap, Node::getColor, Node::getName, HashSet::new)
            .forall((node, path) -> true) // some condition
            .add()
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
