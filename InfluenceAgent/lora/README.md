---
license: other
base_model: Qwen2.5-7B-Instruct
tags:
- llama-factory
- lora
- generated_from_trainer
library_name: peft
model-index:
- name: train_2024-12-08-12-17-11
  results: []
---

<!-- This model card has been generated automatically according to the information the Trainer had access to. You
should probably proofread and complete it, then remove this comment. -->

# train_2024-12-08-12-17-11

This model is a fine-tuned version of [./Qwen2.5-7B-Instruct](https://huggingface.co/./Qwen2.5-7B-Instruct) on the influence_identity and the influence_demo_train datasets.

## Model description

More information needed

## Intended uses & limitations

More information needed

## Training and evaluation data

More information needed

## Training procedure

### Training hyperparameters

The following hyperparameters were used during training:
- learning_rate: 0.0001
- train_batch_size: 4
- eval_batch_size: 8
- seed: 42
- gradient_accumulation_steps: 2
- total_train_batch_size: 8
- optimizer: Adam with betas=(0.9,0.999) and epsilon=1e-08
- lr_scheduler_type: cosine
- num_epochs: 3.0

### Training results



### Framework versions

- PEFT 0.10.0
- Transformers 4.38.2
- Pytorch 2.1.2+cu121
- Datasets 2.16.1
- Tokenizers 0.15.1