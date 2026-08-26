from openai import OpenAI
from config import GROQ_API_KEY

client = OpenAI(
    api_key=GROQ_API_KEY,
    base_url="https://api.groq.com/openai/v1"
)


def generate_response(message: str) -> str:

    response = client.chat.completions.create(
        model="openai/gpt-oss-20b",
        messages=[
            {
                "role": "user",
                "content": message
            }
        ]
    )

    return response.choices[0].message.content