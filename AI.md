# AI-Assisted Development Log

This file records how AI tools were used to improve the project.

## 2026-02-21

### Tool
- ChatGPT (Codex)

### Scope
- Added and refined reminders for upcoming deadlines.
- Added/updated tests for reminder parsing and behavior.
- Improved user-facing reminder error guidance in parser.
- Replaced placeholder user guide content with actual command documentation.

### Increments
1. Implemented reminder support with a dedicated `Remind` task model and integrated command flow.
2. Added tests:
   - `ParserTest` for `remind` default/custom/invalid cases.
   - `TaskListTest` for reminder filtering behavior.
   - `RemindTest` for reminder formatting/getters.
   - `ZhbotTest` and `UiTest` coverage for reminder output path.
3. Improved reminder input errors to include direct usage hints:
   - `Use: remind OR remind <days>.`
4. Updated `docs/README.md` to document real commands and reminder behavior.

### Observations
- What worked:
  - AI-assisted incremental edits were fast for parser switch updates and repetitive test additions.
  - AI was useful for keeping command behavior and tests in sync.
- What did not work as well:
  - User expectations for `remind <keyword>` differed from implementation (`remind <days>`), so clearer error text was needed.
- Estimated time saved:
  - About 30-45 minutes compared to writing and cross-checking all changes manually.
