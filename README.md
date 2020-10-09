# ApiMath
This is a math web service

Examples of requests to this api:

Get

http://localhost:8080/api/v1/math/?expression=2+3*sqrt(4)

http://localhost:8080/api/v1/math/?expression=2+3*sqrt(4)&precision=2

Post

http://localhost:8080/api/v1/math/

body { "expression" : " sqrt(3)*5" , "precision" : 2 }
