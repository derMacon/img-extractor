from flask import jsonify


def parse_map_input(request):
    try:
        # Assuming the request data is in JSON format
        data = request.get_json()

        # Check if data is a dictionary
        if isinstance(data, dict):
            return jsonify(data)
        else:
            return jsonify({'error': 'Invalid input data format'})

    except Exception as e:
        return jsonify({'error': str(e)})
