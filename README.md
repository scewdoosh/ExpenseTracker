# XpenseTracker

Track your payments and get monthly spending reports straight to your Discord server.

## What it does

- Signup/login with JWT auth (stored in HttpOnly cookies)
- Add payments and track your total spending
- Get an automated monthly report in your Discord server via webhook
- All monetary values use BigDecimal — no floating point nonsense

## Tech

**Backend** — Spring Boot, Spring Security, MySQL, Docker  
**Frontend** — React  
**Infra** — Render (backend), Aiven (MySQL)

## Running locally

**Backend**
```bash
docker run -p 8080:8080 \
  -e DB_URL=jdbc:mysql://localhost:3306/yourdb \
  -e DB_USERNAME=root \
  -e DB_PASSWORD=yourpassword \
  -e JWT_SECRET=yourbase64secret \
  -e ALLOWED_ORIGINS=http://localhost:3000 \
  scewdoosh/xpensetracker
```

**Frontend**
```bash
cd frontend/xpense
npm install
npm start
```

Create a `.env` file in `frontend/xpense`:
```
REACT_APP_API_URL=http://localhost:8080/api
```

## Discord Setup

1. Go to your Discord server → Settings → Integrations → Webhooks
2. Create a webhook and copy the URL
3. Paste it in the app under the Discord Webhook section
4. You'll get a spending summary on the 1st of every month

## Environment Variables

| Variable | Description |
|---|---|
| DB_URL | JDBC MySQL connection URL |
| DB_USERNAME | Database username |
| DB_PASSWORD | Database password |
| JWT_SECRET | Base64 encoded secret key |
| ALLOWED_ORIGINS | Frontend URL (CORS) |
