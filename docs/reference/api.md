# API Reference

Overview
List of main API endpoints, expected inputs and outputs.

# API Reference

## Core APIs

### Auth

| Action | Method | Endpoint |
|--------|--------|-----------|
| Login | **POST** | `/api/auth/login` |
| Sign Up | **POST** | `/api/auth/signup` |
| Get Profile | **GET** | `/api/auth/me` |

---

### Project

| Action | Method | Endpoint |
|--------|--------|-----------|
| Create / Read / Update / Delete Project | **CRUD** | `/api/projects/{id}` |
| Get All Projects | **GET** | `/api/projects` |

---

### Files

| Action | Method | Endpoint |
|--------|--------|-----------|
| Get File Tree + Metadata | **GET** | `/api/projects/{id}/files` |
| Download Single File (path encoded) | **GET** | `/api/projects/{id}/files/**` |
| Download Full Project ZIP | **GET** | `/api/projects/{id}/download-zip` |

---

## Additional APIs

### Sharing & Permission

| Action | Method | Endpoint |
|--------|--------|-----------|
| Get All Members | **GET** | `/api/projects/{id}/members` |
| Invite By Email | **POST** | `/api/projects/{id}/members` |
| Change Member Role | **PATCH** | `/api/projects/{id}/members/{userId}` |
| Remove Member | **DELETE** | `/api/projects/{id}/members/{userId}` |

---

### Subscription & Billing

| Action | Method | Endpoint |
|--------|--------|-----------|
| List Available Plans (FREE, PRO) | **GET** | `/api/plans` |
| Current Plan + Limits + Next Billing Date | **GET** | `/api/me/subscription` |
| Create Stripe Checkout Session → redirect | **POST** | `/api/stripe/checkout` |
| Open Customer Portal on Stripe | **POST** | `/api/stripe/portal` |

---

### Usage & Quotas

| Action | Method | Endpoint |
|--------|--------|-----------|
| Tokens Used, Projects Created, Previews Running | **GET** | `/api/usage/today` |
| Current Limits Based on Plan | **GET** | `/api/usage/limits` |

---

### Chat & AI Generation

| Action | Method | Endpoint |
|--------|--------|-----------|
| List Chat Sessions | **GET** | `/api/projects/{id}/chat-sessions` |
| Create New Chat Session | **POST** | `/api/projects/{id}/chat-sessions` |
| Load Full Chat History | **GET** | `/api/chat/sessions/{sessionId}/messages` |
| Chat Stream (SSE) | **POST** | `/api/chat/stream` |

---

### Preview & Runner

| Action | Method | Endpoint |
|--------|--------|-----------|
| Start Live Preview → returns `{ previewUrl, status }` | **POST** | `/api/projects/{id}/preview` |
| Poll Preview Status (CREATING → RUNNING → FAILED) | **GET** | `/api/previews/{previewId}/status` |
| Log Preview — live npm install / Vite HMR (SSE) | **SSE** | `/api/previews/{previewId}/logs` |
| Stop and Delete Preview (cleanup) | **DELETE** | `/api/previews/{previewId}` |

---

## Path Parameters

| Parameter | Description |
|-----------|-------------|
| `id` | Project ID |
| `userId` | User ID |
| `sessionId` | Chat session ID |
| `previewId` | Preview session ID |

---

## Notes
-

