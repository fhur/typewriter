# Defining types

Types can be defined as follows


### Defining types using JSON

```json
{
  "class-name": "User"
  "package-name": "com.fernandohur"
  "doc": "A user is blah"
  "attrs":[
    {
      "name": "firstName",
      "type": "String",
      "doc": "The users name",
      "modifier" : "You can opt. specify a modifier."
    }
  ]
}
```

Will generate the following class

```java
package com.fernandohur

/**
 * A user if blah
 */
public class User {

  /**
   * The users name
   */
  private String firstName;

  /**
   * A getter for the {@link #firstName} attribute.
   */
  public String getFirstName(){
    return firstName;
  }

  /**
   * Sets the user's first name
   */
  public void setFirstName(String firstName){
    this.firstName = firstName;
  }
}

