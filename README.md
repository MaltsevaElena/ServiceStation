# ServiceStation

Проект "Сервисная книжка онлайн"
написан с помощью следующих технологий:
- Java 15
- Git, GitLab
- Maven
- Spring Framework
- Spring DATA, JPA
- ORM, Hibernate
- PostgreSQL
- Docker
- Spring Security
- Spring REST
- Swagger
- Lombok
- Tomcat
- Mock
- Junit  

Проект предоставляет возможность вести 
сервисную книжку автомобиля он-лайн. 
Вносить информацию о том, на каком пробеге 
был произведён тот или иной ремонт,
какие запчасти были замены, какие расходники использовались. 

Также программа позволяет вносить эту информацию
 не только пользователю(владельцу авто), но и СТО.
СТО так же может добавлять информацию о проведённой диагностики. 

В программе реализовано 4 роли:
User: он может регистрироваться, востанавливать пароль, 
изменять данные в личном кабинете, создавать автомобиль, 
добавлять информацию о произведенном ремонте. 

Logist: у него есть доступ к складу и созданию/редактированию/удалению деталей

Employee: может искать автомобили, создавали листы диагностики, 
добавлять информацию о проведённой диагностики, информации о проведённом ремонте, 
искать необходимые детали на складе. 

Director: может создавать/редактировать/удалять СТО, Склад, регистрировать пользователей

                                
