Run in Docker:

1 build the image

docker build -t test/moneytracker .

2 run

docker run -d -p 8915:8915 --name moneytracker --rm test/moneytracker

To stop container run
docker stop moneytracker