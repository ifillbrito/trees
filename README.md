# Abstract Tree Iterators [Prototype]

By extending the abstract classes presented in this project, you can manipulate trees in functional fashion.

## Example:

```java
Node root = createTree();

MyTreeIterator.of(root)
        .forall(node -> node.isRed() && node.isValueEven())
        .modify(node -> node.setValue(node.getValue() * 2))
        .forall(node -> node.isYellow() && node.getValue() > 15)
        .modify(node -> node.setColor(Node.Color.GREEN))
        .forall("/a/b/.*")
        .modify(node -> node.setColor(Node.Color.YELLOW))
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
