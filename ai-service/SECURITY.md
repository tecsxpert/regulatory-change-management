1. Input Injection
Attack: User sends SQL or script
Risk: Data leak or crash
Fix: Input sanitisation
2. Prompt Injection
Attack: "Ignore previous instructions"
Risk: AI gives wrong output
Fix: Filter malicious phrases
3. No Rate Limiting
Attack: Spam API
Risk: Server crash
Fix: Limit requests per minute
4. Broken Authentication
Attack: Unauthorized access
Risk: Data exposure
Fix: JWT validation
5. Sensitive Data Exposure
Attack: Logs contain secrets
Risk: Security breach
Fix: Never log sensitive data