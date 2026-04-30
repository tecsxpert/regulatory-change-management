from flask import Blueprint, request, jsonify
from services.limiter import limiter
from services.ai_service import process_text
from services.logger import logger

test_bp = Blueprint("test", __name__)


@test_bp.route("/test", methods=["POST"])
@limiter.limit("5 per minute")
def test():

    if not request.is_json:
        logger.warning("Request received without JSON format")

        return jsonify({
            "success": False,
            "error": "Request must be in JSON format"
        }), 400

    data = request.json.get("text", "")


    logger.info(f"Received input: {data}")

    result = process_text(data)

    if isinstance(result, dict) and "error" in result:
        logger.warning(f"Malicious input detected: {data}")

        return jsonify({
            "success": False,
            "error": result["error"]
        }), 400

    logger.info(f"Processed result: {result}")

    return jsonify({
        "success": True,
        "data": {
            "input": data,
            "response": result
        }
    })