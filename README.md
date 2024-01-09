# agile-retro

Run Maven -> mvn spring-boot:run

In POSTMAN, 
1> http://localhost:8080/api/retros POST Body: {
    "name": "retro1",
    "summary": "Post release 2024",
    "date": "2014-09-11",
    "participants": null,
    "feedback": null
}

2> http://localhost:8080/api/retros/retro1 GET Body: {
    "item1" : {
        "name": "Gareth",
        "body": "Sprint Objective met",
        "type": "Positive"
    }
}

3> http://localhost:8080/api/retros/retro1/item1 PUT Body: {        
    "name": "Nandini",
    "body": "Sprint Objective met",
    "type": "Positive"

}

4> http://localhost:8080/api/retros GET No Body
