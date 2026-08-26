from flask import Flask
from flask_cors import CORS

from routes import chat

app = Flask(__name__)
CORS(app)

app.register_blueprint(chat)

@app.route("/")
def home():
    return {
        "project": "Conversational AI Backend",
        "status": "Running"
    }

if __name__ == "__main__":
    print("Starting Flask server...")
    app.run(host="0.0.0.0", port=5000, debug=True)