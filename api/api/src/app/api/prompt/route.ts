import Replicate from "replicate";

interface IRequestData {
  prompt: string,
  system_prompt: string
}

export async function GET(request: Request) {
  const replicate = new Replicate({
    auth: `${process.env.TOKEN}`,
  });
  
  const output: any = await replicate.run(
    "meta/llama-2-7b-chat:13c3cdee13ee059ab779f0291d29054dab00a47dad8261375654de5540165fb0",
    {
      input: {
        prompt: "Hello",
        max_new_tokens: 80,
        repetition_penalty: 1,
        temperature: 0.75,
        top_p: 1
      }
    }
  );

  const response_text = output.join("");

  return Response.json({ "msg": response_text });
}

export async function POST(request: Request) {
  const request_data: IRequestData = await request.json();

  const replicate = new Replicate({
    auth: `${process.env.TOKEN}`,
  });

  const model = "meta/llama-2-7b-chat:13c3cdee13ee059ab779f0291d29054dab00a47dad8261375654de5540165fb0";

  const body = {
    input: {
      prompt: request_data.prompt,
      system_prompt: request_data.system_prompt,
      max_new_tokens: 80,
      repetition_penalty: 1,
      temperature: 0.75,
      top_p: 1
    }
  }
  
  const output: any = await replicate.run(model, body);

  const response_text = output.join("");

  return Response.json({ "msg": response_text });
}