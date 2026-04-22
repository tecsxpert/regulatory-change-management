from flask import Blueprint, request, jsonify
import re

test_bp = Blueprint("test", __name__)

def is_malicious(input_text):
    patterns = ["<script>", "DROP TABLE", "ignore previous instructions"]
    return any(p.lower() in input_text.lower() for p in patterns)

@test_bp.route("/test", methods=["POST"])
def test():
    data = request.json.get("text", "")

    if is_malicious(data):
        return jsonify({"error": "Invalid input detected"}), 400

    return jsonify({"message": "Safe input received"})