package loreEngine.math;

import static java.lang.Math.*;

public class Matrix4f {
	
	private float[] elements;
	
	public Matrix4f() {
		this.elements = new float[16];
	}
	
	public Matrix4f mul(Matrix4f other)	{
		
		float data[] = new float[16];
		
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				
				float sum = 0.0f;
				for (int i = 0; i < 4; i++)
					sum += elements[x + i * 4] * other.elements[i + y * 4];
				
				data[x + y * 4] = sum;
			}
		}
		
		elements = data;
		
		return this;
	}
	
	public Matrix4f mul(Matrix4f other, Matrix4f dest)	{
		
		float data[] = new float[16];
		
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				
				float sum = 0.0f;
				for (int i = 0; i < 4; i++)
					sum += elements[x + i * 4] * other.elements[i + y * 4];
				
				data[x + y * 4] = sum;
			}
		}
		
		dest.elements = data;
		
		return this;
	}
	
	
	public static Matrix4f Identity() {
		
		Matrix4f m = new Matrix4f();
		
		m.elements[0 + 0 * 4] = 1.0f;
		m.elements[1 + 1 * 4] = 1.0f;
		m.elements[2 + 2 * 4] = 1.0f;
		m.elements[3 + 3 * 4] = 1.0f;
		
		return m;
	}
	
	public static Matrix4f Translation(Vector3f translation) {
		
		Matrix4f m = Matrix4f.Identity();
		
		m.elements[0 + 3 * 4] = translation.x;
		m.elements[1 + 3 * 4] = translation.y;
		m.elements[2 + 3 * 4] = translation.z;
		
		return m;
	}
	
	/* YAW, PITCH, ROLL*/
	public static Matrix4f Rotation(Vector3f rotation) {
		
		Matrix4f m = Matrix4f.Identity();
		
		float cosX = (float)cos(toRadians(rotation.x));
		float cosY = (float)cos(toRadians(rotation.y));
		float cosZ = (float)cos(toRadians(rotation.z));

		float sinX = (float)sin(toRadians(rotation.x));
		float sinY = (float)sin(toRadians(rotation.y));
		float sinZ = (float)sin(toRadians(rotation.z));

		m.elements[0 + 0 * 4] = cosY * cosZ;
		m.elements[1 + 0 * 4] = (sinX * sinY * cosZ) + (cosX * sinZ);
		m.elements[2 + 0 * 4] = -(cosX * sinY * cosZ) + (sinX * sinZ);

		m.elements[0 + 1 * 4] = -(cosY * sinZ);
		m.elements[1 + 1 * 4] = -(sinX * sinY * sinZ) + (cosX * cosZ);
		m.elements[2 + 1 * 4] = (cosX * sinY * sinZ) + (sinX * cosZ);

		m.elements[0 + 2 * 4] = sinY;
		m.elements[1 + 2 * 4] = -(sinX * cosY);
		m.elements[2 + 2 * 4] = (cosX * cosY);

		return m;
	}
	
	public static Matrix4f Scale(Vector3f scale) {
		
		Matrix4f m = Matrix4f.Identity();
		
		m.elements[0 + 0 * 4] = scale.x;
		m.elements[1 + 1 * 4] = scale.y;
		m.elements[2 + 2 * 4] = scale.z;
		
		return m;
	}

	public float[] getElements() {
		return elements;
	}
	
}