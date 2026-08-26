from flask import Blueprint, request, jsonify

from model import generate_response
from utils import validate_message

chat = Blueprint("chat", __name__)

@chat.route("/chat", methods=["POST"])
def chatbot():
    data = request.get_json()

    message = data.get("message")

    if not validate_message(message):
        return jsonify({
            "status": "error",
            "message": "Message cannot be empty."
        }), 400

    answer = generate_response(message)

    return jsonify({
        "status": "success",
        "reply": answer
    })