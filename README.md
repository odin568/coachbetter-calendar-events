# coachbetter-calendar-events

Dockerized application which provides REST endpoints
`/calendar/team` and `calendar/personal` with optional configurable secret `?secret=MySecret`.

It queries APIs to get events, enriches with available details and creates an ICAL calendar out of it.

This enables a calendar integration with i.e. `ICSx5`.

For configuration options, see example in [docker-compose.yml](./deploy/docker-compose.yml) file.