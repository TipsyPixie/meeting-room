# Meeting Room Reservation

## Requirements

   * Docker ^18
   * Docker Compose ^1.22
   
## Getting Started

   1. Run database container with docker-compose
      ```bash
      $ docker-compose up -d db
      ```
   1. Run application container with docker-compose
      ```bash
      $ docker-compose up -d app
      ```
   1. Check out `docker logs` or log file
      ```bash
      $ docker logs -f meeting-room_app_1
      ```
      or
      ```bash
      $ tail -f ./logs/meeting_room.log
      ```
