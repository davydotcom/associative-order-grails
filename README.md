# Grails many-to-many load order consistency test

It appears that by default grails ignores the fact that teh join table has an ordered index between 2 associations and sorts in random order for each seperate session request

```
sdk use grails 3.1.12
grails test-app --integration
```