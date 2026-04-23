from flask import Blueprint, request, jsonify
from services.limiter import limiter

test_bp = Blueprint("test", __name__)

@test_bp.route("/test", methods=["POST"])
@limiter.limit("5 per minute")
def test():
    data = request.json.get("text", "")
    return jsonify({"message": "Safe input received"})