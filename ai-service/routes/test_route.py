from flask import Blueprint, request, jsonify

test_bp = Blueprint("test", __name__)

@test_bp.route("/test", methods=["POST"])
def test():
    data = request.json.get("text", "")
    return jsonify({"message": "Safe input received"})