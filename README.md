
# Entity-Mapper
EntityMapper is an intelligent object mapping library that automatically maps objects to Entity class.

 Check-Point depends on [[Project Lombok](http://projectlombok.org/), [JODA TIME](https://github.com/JodaOrg/joda-time)]

#### ※EntityMaper does not need a setter and getter

# Installation

#### 1. MAVEN
```xml
 <dependency>
   <groupId>com.github.ckpoint</groupId>
   <artifactId>entity-mapper</artifactId>
   <version>0.0.1</version>
 </dependency>

```
#### 2. GRADLE
```gradle
  compile group: 'com.github.ckpoint', name: 'entity-mapper', version: '0.0.1'
```

## Table of Contents
- [ 1. Create entity with entity-mapper ](#create-entity)
- [ 2. Update entity with entity-mapper ](#update-entity)
- [ 3. Handling specific field or method exceptions ](#handling-field-method)


## Create Entity

#### A. extends MapEntity
#### - MapEntity only has automatic mapping function.
```java

@Entity
@Getter
public class Account extends MapEntity {
    private String name;
    private String email;
    private Long age;
    private Gender gender;

}
```

#### B. extends MapBaseEntity
#### - MapBaseEntity has automatic mapping function and Long type id field.
```java
@Entity
@Getter
public class Account extends MapBaseEntity{
    private String name;
    private String email;
    private Long age;
    private Gender gender;

}
```


#### C. extends MapAuditEntity
#### - MapAuditEntity has automatic mapping function and id, createdAt, updatedAt fields.

```java
@Entity
@Getter
public class Account extends MapAuditEntity{
    private String name;
    private String email;
    private Long age;
    private Gender gender;

}
```

## Update Entity
- The class inheriting EntityMapper has a function called updateFromObj
- EntityMaper does not need a setter method.

### for exmaple

```java
    public void updateEntityTest() {
        AccountModel hsimModel = new AccountModel();
        hsimModel.setAge(25L);
        hsimModel.setEmail("hsim@daou.co.kr");
        hsimModel.setGender("MAN");
        hsimModel.setName("hsim");

        Account hsim = new Account();
        hsim.updateFromObj(hsimModel);
    }

```
## Handling Field Method
- If you want to exclude certain fields or setter functions from automatic mapping, You just need to put the @IgnoreUpdateFromObj annotation.

### for exmaple

```java


@Entity
@Getter
public class Account extends MapAuditModel {
    private String name;
    private String email;

    @IgnoreUpdateFromObj
    private Long age;

    private Gender gender;

    @IgnoreUpdateFromObj
    public void setAge(Long age) {
        this.age = age;
    }
}

```
