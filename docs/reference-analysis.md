# RIYA Reference Analysis & Design Specification

## 1. Visual Source of Truth Overview
Based on visual inspection of the reference command-center interface:
- **Product Name**: RIYA (updated from legacy reference mockup JIYA).
- **Core Aesthetic**: Luminous cybernetic command-center HUD. Dark navy-black technical canvas (`#070B19`), electric cyan glow (`#00E5FF`), deep tactical blue surfaces (`#0C162D`, `#132247`), restrained gold accents (`#FFD54F`, `#FFA000`), crisp white readouts (`#FFFFFF`), and muted blue-gray labels (`#7B8FA8`).
- **Composition Geometry**:
  - **Top Bar**: Glowing RIYA branding, "Your Personal AI Assistant" tagline, real-time status chips (Online, AI Provider: Gemini, Mic State: Listening/Idle), system clock, and settings.
  - **Left Rail**: Primary Navigation (Home, Chat, Tasks, Agents, Tools, Memory, History, Automation, Settings), Quick Access shortcuts (Open YouTube, WhatsApp, Reminder, System Status), and the RIYA Outfit Selector (Default, Business, Tech, Cyber, Casual, Formal) with version tag.
  - **Center Stage**: Dominant female AI avatar with cyan rim glow, audio-reactive HUD rings/waves, live greeting card ("Hi, I'm RIYA. I'm here to make your life easier, smarter and more productive."), Platform connectivity cards (Android Native App [Connected], WebView UI, Termux Backend [FastAPI ports 8000 & 8001], Windows Companion, Web Dashboard).
  - **Interactive Architecture & Engine View**: Visual flow of Client Layer → API Gateway → RIYA Core (Engines: Conversation, LLM, Voice, Vision, Memory, Task, Automation, Security, Event Bus, Agents) → External Integrations → Database (SQLite/Room).
  - **Feature, Tool, Agent & Endpoint Grid**: Key features, Providers (Gemini configured, OpenAI, Local), Tool System, Multi-Agent System (Orchestrator, Research, Coding, Android, Windows, etc.), Avatar states, and real REST/WS endpoints.
  - **Right Drawer / Feed**: Live Messages stream (You, RIYA, System, Agents) and Live Agent Activity stream with execution status.
  - **Bottom Dock**: Real subsystem status chips (Backend, Database, AI, WebSocket, Permissions, Storage), central reactive microphone button ("Tap to talk"), text input with attachment & send, and quick tool buttons.

## 2. Design Tokens
- **Background**: `Color(0xFF070B19)`
- **Surface Level 1 (Card/Panel)**: `Color(0xFF0D172E)`
- **Surface Level 2 (Nested)**: `Color(0xFF132244)`
- **Border / Outline**: `Color(0xFF00E5FF).copy(alpha = 0.4f)`
- **Accent Primary (Cyan)**: `Color(0xFF00E5FF)`
- **Accent Secondary (Gold)**: `Color(0xFFFFD54F)`
- **Accent Tertiary (Green/Success)**: `Color(0xFF00E676)`
- **Accent Warning (Amber)**: `Color(0xFFFF9100)`
- **Accent Error (Red)**: `Color(0xFFFF3D71)`
- **Text Primary**: `Color(0xFFF0F6FC)`
- **Text Secondary**: `Color(0xFF8B9BB4)`
- **Text Highlight**: `Color(0xFF00E5FF)`

## 3. Responsive Strategy
- **Compact Viewport (Phone Portrait)**: Reflows into a mobile-first command center. The central avatar hero, speech bubble, active status chips, quick workspace tabs, live conversation, agent drawer, and bottom mic dock remain front and center with smooth horizontal chips and modal panels.
- **Expanded Viewport (Tablet / Landscape / Desktop)**: Matches the 3-column reference layout with left navigation & outfit selector, center avatar HUD + architecture grid, and right live feed + agent tracker.

## 4. Capability Manifest
- **Text & Voice Assistant (Gemini)**: AVAILABLE via Gemini API client / local fallback with Hindi, English, and Hinglish.
- **Multi-Agent Runtime**: AVAILABLE with Orchestrator, Research, Coding, and Android execution pipelines.
- **Task Management**: AVAILABLE with SQLite / Room persistence.
- **Tool System**: Scoped safe tools (App Launch, Web Search, Notes, Reminders, System Info, Memory).
- **Windows Companion / Termux**: Contract defined, local ports 8000 & 8001 documented and tracked with transparent status (`NEEDS_COMPANION` / `CONFIGURED`).
