# Burger Order Full-Stack Project

Screenshot-based reconstruction with Spring Boot, Spring AI, and React.

## Stack
- Java 17, Spring Boot 3.4.5, Spring Web, Spring Data JPA, H2
- Spring AI 1.0.0 with OpenAI chat integration (optional API key)
- React 18, Vite, Axios

## Run
Backend (JDK 17+ and Maven 3.9+):
```bash
cd backend
mvn spring-boot:run
```
Frontend (Node 18+):
```bash
cd frontend
npm install
npm run dev
```
Open http://localhost:5173. Vite proxies `/api` to Spring Boot at localhost:8080.

H2 console: http://localhost:8080/h2-console
JDBC URL `jdbc:h2:mem:burgerdb`, username `sa`, blank password.

Set `OPENAI_API_KEY` to enable live Spring AI responses. Without it, order management still works and the AI endpoint returns a configuration message.

## API
- POST `/api/burgers/addOrder`
- GET `/api/burgers/getAllOrders`
- GET `/api/burgers/{id}`
- DELETE `/api/burgers/{id}`
- POST `/api/ai/recommend` with `{ "message": "..." }`

The screenshots do not expose the entire original repository. Unseen files have been implemented consistently to create a runnable project. The visible Requirement 5 TODO comment block is retained in `AddBurger.jsx`.
