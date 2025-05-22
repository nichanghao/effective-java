package net.sunday.effective.groovy


class Person {
    // 无 name 属性的定义
    void myMethod() {
        println "Hello".nonExistingMethod() // 触发静态检查：报错（方法不存在）
        def person = new Person()
        println(person.name) // 静态类型检查会报错：无法解析属性 "name"
    }
}
