# dbc-ejb
Design by Contract implementation for EJB

Please see the [CityServiceEngineImpl](https://github.com/canmogol/dbc-ejb/blob/master/src/main/java/com/fererlab/city/service/CityServiceEngineImpl.java) class for Invariant, Requires and Ensures implementations.

### Invariant
<b>bean</b> refers to <b>this</b> as in the instance of annotated class. It is expected that the conditions in the invariant should satisfy before and after.
```java
@Invariant({
        "bean.shouldReturnEmptyCityDTO() != null",
        "bean.shouldReturnEmptyCityDTO().name == null"
})
```

### Requires
<b>params</b> refers to the parameters of the annotated method. It is expected that the conditions in the requires should satisfy before method execution.
```java
@Requires({
        "params[0]!=null",
        "params[0]!=\"\""
})
public CityDTO create(String name){
  //...
}
```
### Ensures
<b>result</b> refers to the return value(cityDTO) of the annotated method. It is expected that the conditions in the ensures should satisfy after method execution.
```java
@Ensures({
        "result!=null",
        "result.name==params[0]"
})
public CityDTO create(String name){
  //...
  return cityDTO;
}
```
