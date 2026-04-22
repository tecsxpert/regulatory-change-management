from flask import Flask
from flask_limiter import Limiter
from flask_limiter.util import get_remote_address
from routes.test_route import test_bp

app = Flask(__name__)

limiter = Limiter(get_remote_address, app=app, default_limits=["30 per minute"])

app.register_blueprint(test_bp)

@app.route("/")
def home():
    return "AI Service Running"

if __name__ == "__main__":
    app.run(port=5000)
    